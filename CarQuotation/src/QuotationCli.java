import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class QuotationCli {

    // 화면을 깨끗하게 지워주는 메소드
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    // 현재까지 선택한 항목들을 '장바구니'처럼 보여주는 메소드
    public static void displayShoppingCart(Car car, Trim trim, List<Option> selectedOptions, long currentPrice, NumberFormat formatter) {
        System.out.println("=======================================================");
        System.out.println(" 현재 선택하신 내역 (장바구니)");
        System.out.println("-------------------------------------------------------");
        System.out.printf("  %-12s | %s\n", "차종", car.name);
        System.out.printf("  %-12s | %s\n", "트림", trim.name);

        if (!selectedOptions.isEmpty()) {
            System.out.println("  -----------------------------------------------------");
            System.out.printf("  %-12s | \n", "옵션");
            for (Option opt : selectedOptions) {
                 System.out.printf("  %-12s |    - %s (+%,d원)\n", "", opt.name, opt.price);
            }
        }
        System.out.println("-------------------------------------------------------");
        System.out.printf("  합계: %s원\n", formatter.format(currentPrice).replace("₩", ""));
        System.out.println("=======================================================\n");
    }

    // 사용자로부터 숫자 입력을 안전하게 받는 메소드
    private static int getNumericInput(Scanner scanner) {
        while (true) {
            try {
                System.out.print("\n입력: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("잘못된 입력입니다. 숫자를 입력해주세요: ");
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
        boolean isRunning = true;

        while(isRunning) {
            clearScreen();
            System.out.println("원더굿라이프 신차 견적 프로그램을 시작합니다.");

            System.out.println("\n--- 0. 차종 선택 ---");
            List<String> carNames = CarDatabase.getCarNames();
            for (int i = 0; i < carNames.size(); i++) System.out.printf("%d. %s\n", i + 1, carNames.get(i));
            int carChoice = getNumericInput(scanner) - 1;
            if (carChoice < 0 || carChoice >= carNames.size()) {
                System.out.println("잘못된 번호입니다. 2초 후 다시 시작합니다.");
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                continue;
            }
            
            Car selectedCar = CarDatabase.getCar(carNames.get(carChoice));
            
            clearScreen();
            System.out.printf("차종 '%s'를 선택하셨습니다.\n", selectedCar.name);
            System.out.println("\n--- 1. 트림 선택 ---");
            for (int i = 0; i < selectedCar.trims.size(); i++) System.out.printf("%d. %s\n", i + 1, selectedCar.trims.get(i));
            int trimChoice = getNumericInput(scanner) - 1;
            if (trimChoice < 0 || trimChoice >= selectedCar.trims.size()) {
                System.out.println("잘못된 번호입니다. 2초 후 다시 시작합니다.");
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                continue;
            }
            Trim selectedTrim = selectedCar.trims.get(trimChoice);

            long totalPrice = selectedTrim.price;
            List<Option> selectedOptionsList = new ArrayList<>();
            List<Option> availableOptions = new ArrayList<>(selectedCar.colors);
            availableOptions.addAll(selectedTrim.availableOptions);

            while (true) {
            	
                clearScreen();
                displayShoppingCart(selectedCar, selectedTrim, selectedOptionsList, totalPrice, currencyFormatter);
                System.out.println("--- 색상 및 추가 옵션 선택 ---");
                System.out.println("원하는 항목의 번호를 입력하여 추가/해제 하세요 (완료 시 0 입력)");

                for (int i = 0; i < availableOptions.size(); i++) {
                    Option opt = availableOptions.get(i);
                    boolean isSelected = selectedOptionsList.contains(opt);
                    String prerequisitesInfo = opt.prerequisites.isEmpty() ? "" : " (선행: " + String.join(", ", opt.prerequisites) + ")";
                    System.out.printf("%2d. %-40s %s %s\n", i + 1, opt, prerequisitesInfo, isSelected ? "[선택됨]" : "");
                }
                
                int optionChoiceIdx = getNumericInput(scanner) - 1;

                if (optionChoiceIdx == -1) break;
                if (optionChoiceIdx < 0 || optionChoiceIdx >= availableOptions.size()) {
                    System.out.println("잘못된 번호입니다. 2초 후 다시 시도합니다.");
                    try { Thread.sleep(2000); } catch (InterruptedException e) {}
                    continue;
                }

                Option chosenOption = availableOptions.get(optionChoiceIdx);
                
                if (selectedOptionsList.contains(chosenOption)) {
                    selectedOptionsList.remove(chosenOption);
                    totalPrice -= chosenOption.price;
                } else {
                    boolean isColorOption = selectedCar.colors.contains(chosenOption);
                    if (isColorOption) {
                        Optional<Option> previouslySelectedColor = selectedOptionsList.stream()
                            .filter(opt -> selectedCar.colors.contains(opt))
                            .findFirst();
                        
                        if (previouslySelectedColor.isPresent()) {
                            totalPrice -= previouslySelectedColor.get().price;
                            selectedOptionsList.remove(previouslySelectedColor.get());
                        }
                    }

                    boolean prerequisitesMet = true;
                    if (!chosenOption.prerequisites.isEmpty()) {
                        for(String preqName : chosenOption.prerequisites) {
                            if (selectedOptionsList.stream().noneMatch(o -> o.name.equals(preqName))) {
                                System.out.printf("!! 경고: '%s' 옵션을 선택하려면 '%s' 옵션이 먼저 선택되어야 합니다. (2초 후 계속)\n", chosenOption.name, preqName);
                                prerequisitesMet = false;
                                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                                break;
                            }
                        }
                    }
                    if (prerequisitesMet) {
                        selectedOptionsList.add(chosenOption);
                        totalPrice += chosenOption.price;
                    }
                }
            }

            // --- 최종 수정: 사용자가 색상을 선택하지 않았을 경우 기본 색상 자동 추가 ---
            boolean hasColorSelection = selectedOptionsList.stream().anyMatch(opt -> selectedCar.colors.contains(opt));
            if (!hasColorSelection) {
                selectedCar.colors.stream()
                    .filter(color -> color.price == 0)
                    .findFirst()
                    .ifPresent(selectedOptionsList::add);
            }
            // -------------------------------------------------------------------
            
            clearScreen();
            displayShoppingCart(selectedCar, selectedTrim, selectedOptionsList, totalPrice, currencyFormatter);
            System.out.println("견적이 완료되었습니다.");

            while(true) {
                System.out.print("\n새로운 견적을 내시겠습니까? (1: 예, 2: 아니오): ");
                int restartChoice = getNumericInput(scanner);
                if (restartChoice == 1) {
                    break;
                } else if (restartChoice == 2) {
                    isRunning = false;
                    break;
                } else {
                    System.out.println("잘못된 입력입니다. 1 또는 2를 입력해주세요.");
                }
            }
        }

        System.out.println("\n프로그램을 종료합니다. 이용해주셔서 감사합니다.");
        scanner.close();
    }
}
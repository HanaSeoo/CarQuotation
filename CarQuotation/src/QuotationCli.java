import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class QuotationCli {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.KOREA);

        System.out.println("🚗 기아 EV 시리즈 견적 프로그램을 시작합니다.");

        // 0. 차종 선택
        System.out.println("\n--- 0. 차종 선택 ---");
        List<String> carNames = CarDatabase.getCarNames();
        for (int i = 0; i < carNames.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, carNames.get(i));
        }
        System.out.print("원하는 차종의 번호를 입력하세요: ");
        int carChoice = Integer.parseInt(scanner.nextLine()) - 1;
        Car selectedCar = CarDatabase.getCar(carNames.get(carChoice));

        System.out.printf("\n%s 모델의 견적을 시작합니다.\n", selectedCar.name);
        
        // 1. 트림 선택
        System.out.println("\n--- 1. 트림 선택 ---");
        for (int i = 0; i < selectedCar.trims.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, selectedCar.trims.get(i));
        }
        System.out.print("원하는 트림의 번호를 입력하세요: ");
        int trimChoice = Integer.parseInt(scanner.nextLine()) - 1;
        Trim selectedTrim = selectedCar.trims.get(trimChoice);

        long totalPrice = selectedTrim.price;
        List<Option> finalSelections = new ArrayList<>();
        
        // 2. 색상 선택
        System.out.println("\n--- 2. 외장 색상 선택 ---");
        for(int i = 0; i < selectedCar.colors.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, selectedCar.colors.get(i));
        }
        System.out.print("원하는 색상의 번호를 입력하세요: ");
        int colorChoice = Integer.parseInt(scanner.nextLine()) - 1;
        Option selectedColor = selectedCar.colors.get(colorChoice);
        totalPrice += selectedColor.price;
        finalSelections.add(selectedColor);


        // 3. 옵션 선택
        System.out.println("\n--- 3. 추가 옵션 선택 ---");
        List<Option> availableOptions = selectedTrim.availableOptions;
        List<Option> selectedOptionsList = new ArrayList<>();

        while (true) {
            System.out.println("\n선택 가능한 옵션 목록:");
            for (int i = 0; i < availableOptions.size(); i++) {
                Option opt = availableOptions.get(i);
                boolean isSelected = selectedOptionsList.contains(opt);
                String prerequisitesInfo = opt.prerequisites.isEmpty() ? "" : " (선행: " + String.join(", ", opt.prerequisites) + ")";
                System.out.printf("%d. %s %s %s\n", i + 1, opt, prerequisitesInfo, isSelected ? "[✔ 선택됨]" : "");
            }
            System.out.print("\n추가/해제할 옵션 번호를 입력하세요 (완료 시 0 입력): ");
            int optionChoice = Integer.parseInt(scanner.nextLine());

            if (optionChoice == 0) break;
            if (optionChoice < 1 || optionChoice > availableOptions.size()) {
                System.out.println("잘못된 번호입니다. 다시 입력해주세요.");
                continue;
            }

            Option chosenOption = availableOptions.get(optionChoice - 1);
            
            if (selectedOptionsList.contains(chosenOption)) {
                // 옵션 해제
                selectedOptionsList.remove(chosenOption);
                totalPrice -= chosenOption.price;
                System.out.printf("-> '%s' 옵션이 해제되었습니다.\n", chosenOption.name);
            } else {
                // 옵션 추가
                boolean prerequisitesMet = true;
                if (!chosenOption.prerequisites.isEmpty()) {
                    for(String preqName : chosenOption.prerequisites) {
                        if (selectedOptionsList.stream().noneMatch(o -> o.name.equals(preqName))) {
                            System.out.printf("!! 경고: '%s' 옵션을 선택하려면 '%s' 옵션이 먼저 선택되어야 합니다.\n", chosenOption.name, preqName);
                            prerequisitesMet = false;
                            break;
                        }
                    }
                }
                if (prerequisitesMet) {
                    selectedOptionsList.add(chosenOption);
                    totalPrice += chosenOption.price;
                    System.out.printf("-> '%s' 옵션이 추가되었습니다.\n", chosenOption.name);
                }
            }
        }
        finalSelections.addAll(selectedOptionsList);
        
        // 4. 최종 견적 출력
        System.out.println("\n--- 📜 최종 견적서 ---");
        System.out.println("차종: " + selectedCar.name);
        System.out.println("트림: " + selectedTrim.name);
        System.out.println("\n[선택 항목]");
        for (Option opt : finalSelections) {
             if(opt.price > 0) System.out.printf("- %s: %,d원\n", opt.name, opt.price);
        }
        System.out.println("--------------------------------");
        System.out.printf("총 차량 가격: %s원\n", currencyFormatter.format(totalPrice).replace("₩", ""));
        System.out.println("--------------------------------");

        scanner.close();
    }
}
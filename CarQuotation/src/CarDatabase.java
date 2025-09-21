import java.util.*;

// 모든 차량의 데이터를 생성하고 보관하는 클래스입니다.
public class CarDatabase {

    private static final Map<String, Car> cars = new HashMap<>();

    static {
        initializeEv3();
        initializeEv4();
        initializeEv5();
        initializeEv6();
        initializeEv6Gt();
    }

    private static void initializeEv3() {
        // 옵션
        Option style = new Option("스타일", 940000);
        Option comfort = new Option("컴포트", 1380000);
        Option convenience = new Option("컨비니언스", 1190000);
        Option nineteenInch = new Option("19인치 휠&타이어", 490000);
        Option monitoring = new Option("모니터링", 1040000);
        Option driveWise = new Option("드라이브 와이즈", 1090000);
        Option wideSunroof = new Option("와이드 선루프", 640000, "스타일");
        Option builtInCam = new Option("빌트인 캠 2", 450000);
        Option hud = new Option("헤드업 디스플레이", 590000);

        List<Option> airOptions = Arrays.asList(style, comfort, convenience, nineteenInch, monitoring, driveWise, wideSunroof, builtInCam, hud);
        List<Option> earthGtOptions = Arrays.asList(nineteenInch, monitoring, driveWise, wideSunroof, builtInCam, hud); // 어스와 GT-line은 일부 옵션이 기본

        // 트림
        List<Trim> trims = Arrays.asList(
            new Trim("에어 스탠다드", 39950000, airOptions),
            new Trim("에어 롱레인지", 44150000, airOptions),
            new Trim("어스 스탠다드", 43900000, earthGtOptions),
            new Trim("어스 롱레인지", 48100000, earthGtOptions),
            new Trim("GT-line 스탠다드", 44750000, earthGtOptions),
            new Trim("GT-line 롱레인지", 48950000, earthGtOptions)
        );
        // 색상
        List<Option> colors = Arrays.asList(
            new Option("기본 컬러", 0),
            new Option("스노우 화이트 펄", 80000),
            new Option("아이보리 매트 실버", 300000)
        );
        cars.put("EV3", new Car("EV3", trims, colors));
    }

    private static void initializeEv4() {
        // 옵션
        Option style = new Option("스타일", 740000);
        Option comfort = new Option("컴포트", 890000);
        Option convenience = new Option("컨비니언스", 890000);
        Option nineteenInch = new Option("19인치 휠&타이어", 490000);
        Option monitoring = new Option("모니터링", 1040000);
        Option driveWise = new Option("드라이브 와이즈", 1280000);
        Option utility = new Option("유틸리티", 400000);
        Option wideSunroof = new Option("와이드 선루프", 640000, "스타일");
        Option harmanKardon = new Option("하만카돈 프리미엄 사운드", 640000);
        Option builtInCam = new Option("빌트인 캠 2 플러스", 450000);
        Option hud = new Option("헤드업 디스플레이", 590000, "모니터링");
        
        List<Option> airOptions = Arrays.asList(style, comfort, convenience, nineteenInch, monitoring, driveWise, utility, wideSunroof, builtInCam, hud);
        List<Option> earthOptions = Arrays.asList(nineteenInch, driveWise, utility, wideSunroof, harmanKardon, builtInCam, hud);
        List<Option> gtLineOptions = Arrays.asList(driveWise, utility, wideSunroof, harmanKardon, builtInCam, hud);
        // 트림
        List<Trim> trims = Arrays.asList(
            new Trim("에어 스탠다드", 40420000, airOptions),
            new Trim("에어 롱레인지", 44620000, airOptions),
            new Trim("어스 스탠다드", 45010000, earthOptions),
            new Trim("어스 롱레인지", 49210000, earthOptions),
            new Trim("GT-line 스탠다드", 46110000, gtLineOptions),
            new Trim("GT-line 롱레인지", 50310000, gtLineOptions)
        );
        // 색상
        List<Option> colors = Arrays.asList(
            new Option("기본 컬러", 0),
            new Option("스노우 화이트 펄", 80000),
            new Option("아이보리 매트 실버", 300000),
            new Option("요트 매트 블루", 300000)
        );
        cars.put("EV4", new Car("EV4", trims, colors));
    }

    private static void initializeEv5() {
        // 옵션
        Option style = new Option("스타일", 890000);
        Option comfort1 = new Option("컴포트 I", 1380000);
        Option comfort2 = new Option("컴포트 II", 690000);
        Option nineteenInch = new Option("19인치 휠&타이어", 300000);
        Option smartConnect_Air = new Option("스마트 커넥트(Air)", 1340000);
        Option smartConnect_Earth = new Option("스마트 커넥트(Earth/GT)", 740000);
        Option monitoring = new Option("모니터링", 1380000);
        Option driveWise = new Option("드라이브 와이즈", 1340000);
        Option panoramaSunroof_Air = new Option("파노라마 선루프(Air)", 1190000);
        Option panoramaSunroof_Earth = new Option("파노라마 선루프(Earth/GT)", 1090000);
        Option harmanKardon = new Option("하만카돈 프리미엄 사운드", 640000);
        Option builtInCam_Air = new Option("빌트인 캠 2 플러스(Air)", 590000);
        Option builtInCam_Earth = new Option("빌트인 캠 2 플러스(Earth/GT)", 450000);

        List<Option> airOptions = Arrays.asList(style, comfort1, nineteenInch, smartConnect_Air, monitoring, driveWise, panoramaSunroof_Air, builtInCam_Air);
        List<Option> earthOptions = Arrays.asList(comfort2, nineteenInch, smartConnect_Earth, monitoring, driveWise, panoramaSunroof_Earth, harmanKardon, builtInCam_Earth);
        List<Option> gtLineOptions = Arrays.asList(comfort2, smartConnect_Earth, monitoring, driveWise, panoramaSunroof_Earth, harmanKardon, builtInCam_Earth);
        // 트림
        List<Trim> trims = Arrays.asList(
            new Trim("에어 롱레인지", 48550000, airOptions),
            new Trim("어스 롱레인지", 52300000, earthOptions),
            new Trim("GT-line 롱레인지", 53400000, gtLineOptions)
        );
        // 색상
        List<Option> colors = Arrays.asList(
            new Option("기본 컬러", 0),
            new Option("스노우 화이트 펄", 80000),
            new Option("아이스버그 매트 그린", 300000)
        );
        cars.put("EV5", new Car("EV5", trims, colors));
    }

     private static void initializeEv6() {
        // 옵션
        Option driveWise_Light = new Option("드라이브 와이즈(라이트)", 1100000);
        Option driveWise_AirUp = new Option("드라이브 와이즈(에어 이상)", 650000);
        Option wideSunroof = new Option("와이드 선루프", 640000);
        Option meridianSound = new Option("메리디안 사운드", 990000);
        Option dualMotor = new Option("듀얼 모터 4WD", 2470000);
        Option builtInCam = new Option("빌트인 캠 2", 450000);
        Option smartConnect = new Option("스마트 커넥트", 700000);
        Option twentyInch = new Option("20인치 휠&타이어", 400000);

        List<Option> lightOptions = Arrays.asList(driveWise_Light, new Option("컴포트", 1040000), new Option("컨비니언스 I", 500000));
        List<Option> airOptions = Arrays.asList(driveWise_AirUp, new Option("컨비니언스 II", 900000), new Option("하이테크", 690000));
        List<Option> earthOptions = Arrays.asList(driveWise_AirUp, wideSunroof, meridianSound, builtInCam, dualMotor, smartConnect, twentyInch);
        List<Option> gtLineOptions = Arrays.asList(driveWise_AirUp, wideSunroof, meridianSound, builtInCam, dualMotor, smartConnect, new Option("스웨이드 컬렉션", 400000));
        // 트림
        List<Trim> trims = Arrays.asList(
            new Trim("라이트 스탠다드", 46600000, lightOptions),
            new Trim("라이트 롱레인지", 50600000, lightOptions),
            new Trim("에어 스탠다드", 51400000, airOptions),
            new Trim("에어 롱레인지", 55400000, airOptions),
            new Trim("어스 롱레인지", 59400000, earthOptions),
            new Trim("GT-line 롱레인지", 60000000, gtLineOptions)
        );
        // 색상
        List<Option> colors = Arrays.asList(
            new Option("기본 컬러", 0),
            new Option("스노우 화이트 펄", 80000),
            new Option("문스케이프 매트 그레이", 300000),
            new Option("요트 매트 블루", 300000)
        );
        cars.put("EV6", new Car("EV6", trims, colors));
    }

    private static void initializeEv6Gt() {
        // 옵션
        List<Option> gtOptions = Arrays.asList(
            new Option("와이드 선루프", 640000),
            new Option("스마트 커넥트", 1300000)
        );
        // 트림
        List<Trim> trims = Collections.singletonList(
            new Trim("GT", 72300000, gtOptions)
        );
        // 색상
        List<Option> colors = Arrays.asList(
            new Option("기본 컬러", 0),
            new Option("스노우 화이트 펄", 80000),
            new Option("요트 매트 블루", 300000)
        );
        cars.put("EV6 GT", new Car("EV6 GT", trims, colors));
    }

    public static List<String> getCarNames() {
        return new ArrayList<>(cars.keySet());
    }

    public static Car getCar(String name) {
        return cars.get(name);
    }
}
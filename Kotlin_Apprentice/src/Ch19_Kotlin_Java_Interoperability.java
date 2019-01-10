import java.util.HashMap;

class JavaApplication {
    public static void main(String[] args) {
        //Making your Kotlin Code Java-friendly
        Ch19_User user = new Ch19_User(); //기존 Java 구문으로 새 인스턴스 생성
        user.setFirstName("Testy");
        user.setLastName("McTesterson");
        //Java는 명시적으로 setter를 추가해 줘야 한다.

        //코드를 입력하다보면 Kotlin의 멤버들에 맞춰 getter, setter가 자동 완성 된다.
        //Kotlin에서 val로 생성된 상수는 생성자에서 설정 된 후 변경되지 않으므로, Setter가 생성되지 않는다.

        Ch19_Address address = new Ch19_Address( //Address 생성
                "345 Nonexistent Avenue NW",
                null,
                "Washington",
                "DC",
                "20016",
                AddressType.Shipping
        );

        UserExtensions.addOrUpdateAddress(user, address);
        LabelPrinter.printLabelFor(user);
        //두 함수는 모두 Kotlin에 정의되어 있다.

        Ch19_Address.JSONKeys keys = Ch19_Address.JSONKeys.INSTANCE;
        //Kotlin은 Java에서 액세스할 때, 클래스에 접근할 수 있도록 INSTANCE 변수를 생성한다.

        HashMap<String, Object> addressJSON = new HashMap<>();

        addressJSON.put(keys.streetLine1, address.streetLine1);
        addressJSON.put(keys.streetLine2, address.streetLine2);
        addressJSON.put(keys.city, address.city);
        addressJSON.put(keys.stateOrProvince, address.stateOrProvince);
        addressJSON.put(keys.postalCode, address.postalCode);
        addressJSON.put(keys.country, address.country);
        addressJSON.put(keys.addressType, address.addressType.name());

        System.out.println("Address JSON:\n" + addressJSON);





        //"Static" values and functions from Kotlin
        System.out.println("Sample first line of address: " + Ch19_Address.sampleFirstLine);

        Ch19_Address canadian = Ch19_Address.canadianSample(AddressType.Shipping);
//        Ch19_Address canadian = Ch19_Address.Companion.canadianSample(AddressType.Shipping);
        //@JvmStatic 주석 없이 처리하려면 위와 같이 처리해 줘야 한다. 기본 companion object 이름 사용하여 액세스한다.

        System.out.println(canadian);
    }
}
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

//public이므로 외부에서 get, set 메서드로 접근할 수 있다.

public class Ch19_User {
    private String firstName;
    private String lastName;
//    private String city;
//    private String country;
    //Kotlin의 Address 클래스에서 주소 정보를 관리한다.

    private List<Ch19_Address> addresses = new ArrayList<>(); //주소 목록

    @Nullable //Null이 될 수 있음을 나타내는 주석
    //Nullability 주석을 Java에 추가할 때, 충돌이 날 수도 있으므로 주의해야 한다.
    //Jaca는 변수에 초기값을 설정해 두지 않으면 null이 될 수 있음을 유의해야 한다.
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }

    @NotNull //Null이 될 수 없음을 나타내는 주석
    public List<Ch19_Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Ch19_Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
//        return firstName + " " + lastName + "\n" + city + ", " + country;
//        return firstName + " " + lastName;
//        return Ch19_UserExtensionsKt.getFullName(this);
        //Kotlin에서 구현한 extension에서 해당 속성을 가져온다.
        //Java에서는 extension이 없기 때문에 해당 함수의 매개변수로 전달된다.
        //기본적으로 다른 이름을 설정하지 않는 한, Kotlin은 해당 함수(extension)이 있는 파일을 FileNameKt이라는 래퍼 클래스의 정적 메서드로 제공한다.

//        return UserExtensions.getFullName(this);
//        return UserExtensions.getFullName(this) + " - Addresses: " + addresses.size();
        return UserExtensions.getFullName(this) + " - Addresses: " + addresses.size() + "\n" + allAddresses();
    }

    public String allAddresses() {
        StringBuilder builder = new StringBuilder();
        for (Ch19_Address address : addresses) {
            builder.append(address.addressType.name() + " address:\n");
            builder.append(LabelPrinter.labelFor(this, address.addressType));
            //Free function과 extension file이 interop에서 작동한다.
            //래퍼 클래스 LabelPrinterKt는 단순히 전역 함수로 작성되는 것이 아니라 정적 메서드로 추가된 Free function으로 생성된다.
            //extension이 없으므로 전달해야 하는 추가 매개 변수가 없다.
            //또, Free function만 있는 파일에서 래퍼 클래스에 대해 자동 생성된 이름은 FileNameKt이다.
            //주석을 추가해 이름을 변경해 줄 수 있다.
        }

        return builder.toString();
    }
}

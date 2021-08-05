package lunga.com.buddielam;

public class User {

    String full_name, email, gender, age, dob, phone, location, medication, nextAppointment;

    public User() {
    }

    //TODO: ADD "MEDICATION" AND "NEXT Appointment"
    public User(String full_name, String email, String gender, String age, String dob, String phone, String location, String medication, String nextAppointment) {
        this.full_name = full_name;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.dob = dob;
        this.phone = phone;
        this.location = location;
        this.medication = medication;
        this.nextAppointment = nextAppointment;

    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    //
    public String getMedication() {
        return medication;
    }

    public void setMedication(String Medication) {
        this.medication = Medication;
    }

    //
    public String getNextAppointment() {
        return nextAppointment;
    }

    public void setNextAppointment(String NextAppointment) { this.nextAppointment = NextAppointment;
    }

}

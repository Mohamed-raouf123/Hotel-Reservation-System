import java.time.LocalDate;

public class Guest {
    public enum Gender {
        MALE,
        FEMALE
    }

        private String username;
        private String password;
        private LocalDate dateOfBirth;
        private String address;
        private Double balance;
        private Gender gender;
        private long phone;
        private String guestId;

        public void setUsername(String username){
            this.username = username;
        }
        public void setPassword(String password){
            this.password = password;
        }
        public void setAddress( String address){
            this.address=address;
        }
        public void setBalance(double balance){
            this.balance=balance;
        }
        public void setPhone(Long phone){
        this.phone=phone;
        }
        public void setGuestId(String guestId){
            this.guestId=guestId;
        }
        public void setGender(Gender gender){
            this.gender=gender;
        }
        public void setDateOfBirth(LocalDate dob){
            this.dateOfBirth=dob;
        }

        public Double getBalance() {
            return balance;
        }

        public Gender getGender() {
            return gender;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public long getPhone() {
            return phone;
        }

        public String getUsername() {
            return username;
        }

        public String getAddress() {
            return address;
        }

        public String getGuestId() {
            return guestId;
        }

        public String getPassword() {
            return password;
        }
    }



package ru.stqa.pft.addressbook.model;

public class ContactData {

    private final String id;
    private final String firstname;
    private final String lastname;
    private final String company;
    private final String address;
    private final String homephone;
    private final String mobilephone;
    private String group;

    public ContactData(String id, String firstname, String lastname, String company, String address, String homephone, String mobilephone, String group) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.address = address;
        this.homephone = homephone;
        this.mobilephone = mobilephone;
        this.group = group;
    }

    public ContactData(String firstname, String lastname, String company, String address, String homephone, String mobilephone, String group) {
        this.id = null;
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.address = address;
        this.homephone = homephone;
        this.mobilephone = mobilephone;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getHomephone() {
        return homephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        return address != null ? address.equals(that.address) : that.address == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

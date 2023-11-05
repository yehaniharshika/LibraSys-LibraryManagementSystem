package lk.ijse.LibraSys.dto.tm;

import java.time.LocalDate;

public class MembershipFeeTm {
    private String id;
    private String name;
    private double amount;
    private LocalDate date;
    private String status;

    public MembershipFeeTm() {

    }

    public MembershipFeeTm(String id,String name,double amount,LocalDate date,String status ){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date =date;
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "MembershipFeeTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}

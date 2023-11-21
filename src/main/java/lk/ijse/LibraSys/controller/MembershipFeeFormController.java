package lk.ijse.LibraSys.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.db.DbConnection;
import lk.ijse.LibraSys.dto.MembershipFeeDto;
import lk.ijse.LibraSys.dto.tm.MembershipFeeTm;
import lk.ijse.LibraSys.model.MembershipFeeModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MembershipFeeFormController {

    public TextField txtDate;
    public Label lblPaidDate;
    public TableView tblMembershipFee;

    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colFeeid;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStatus;

    private MembershipFeeModel  membershipFeeModel = new MembershipFeeModel();

    public  void initialize() {
        setDate();
        loadAllMembershipFee();
        setCellValueFactory();

    }

    private void setCellValueFactory() {
        colFeeid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new  PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAllMembershipFee() {
        var model = new  MembershipFeeModel();

        ObservableList<MembershipFeeTm> obList = FXCollections.observableArrayList();

        try{
            List<MembershipFeeDto> feeList = model.getAllMemberShipFee();
            for(MembershipFeeDto dto : feeList){
                obList.add(
                        new MembershipFeeTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAmount(),
                                dto.getDate(),
                                dto.getStatus()
                        )
                );
            }
            tblMembershipFee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setDate() {

        lblPaidDate.setText(String.valueOf(LocalDate.now()));
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        setDate();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAmount.setText("");
        lblPaidDate.setText("");
        txtStatus.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id =txtId.getText();

       try {
           boolean isDeleted = membershipFeeModel.deleteMembershipFee(id);

           if(isDeleted){
              new Alert(Alert.AlertType.CONFIRMATION,"Deleted successfully!!").show();
              loadAllMembershipFee();
           }
       } catch (SQLException e) {
           new Alert(Alert.AlertType.CONFIRMATION,"Deleted not successfully").show();
       }

    }


    @FXML
    void btnSaveOnAction(ActionEvent event){

        String id =txtId.getText();
        String name = txtName.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        LocalDate date = LocalDate.parse(lblPaidDate.getText());
        String status = txtStatus.getText();

        var dto = new MembershipFeeDto(id,name,amount,date,status);

        try {
            boolean isSaved = membershipFeeModel.saveMembersipFee(dto);

            if(isSaved){
               new Alert(Alert.AlertType.CONFIRMATION,"success!!").show();
               clearFields();
               setDate();
               loadAllMembershipFee();
               setCellValueFactory();

            }

        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private boolean validateMembershipFee(){
        String fee_id = txtId.getText();
        Pattern compile = Pattern.compile("[F][0-9]{3,}");
        Matcher matcher =  compile.matcher(fee_id);
        boolean matches = matcher.matches();
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"Invalid fee ID!!!").show();
            return  false;
        }

        String name = txtName.getText();
        boolean matches1 = Pattern.matches("[A-Za-z\\s]{1,}",name);
        if (!matches1){
            new Alert(Alert.AlertType.ERROR,"Invalid name!!!").show();
            return  false;
        }

        String status = txtStatus.getText();
        boolean matches2 = Pattern.matches("(Anually|Monthly|For six monthes)" , status);
        if (!matches2){
            new Alert(Alert.AlertType.ERROR,"Invalid status!!!").show();
        }
        return  true;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event){

        String id =txtId.getText();
        String name =txtName.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        LocalDate date = LocalDate.parse(lblPaidDate.getText());
        String status =txtStatus.getText();

        var dto = new MembershipFeeDto(id,name,amount,date,status);
        try {
            boolean isUpdated = membershipFeeModel.updateMembershipfee(dto);

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"successfully updated!").show();
                clearFields();
                setDate();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            MembershipFeeDto membershipFeeDto = membershipFeeModel.searchMembershipFee(id);
            if(membershipFeeDto != null){
                txtId.setText(membershipFeeDto.getId());
                txtName.setText(membershipFeeDto.getName());
                txtAmount.setText(String.valueOf(membershipFeeDto.getAmount()));
                lblPaidDate.setText(String.valueOf(membershipFeeDto.getDate()));
                txtStatus.setText(membershipFeeDto.getStatus());
            }else {
                new  Alert(Alert.AlertType.INFORMATION,"member not found!!").show();

            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
        stage.show();
    }
}
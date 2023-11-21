package lk.ijse.LibraSys.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.LibraSys.dto.MemberDto;
import lk.ijse.LibraSys.dto.MembershipFeeDto;
import lk.ijse.LibraSys.dto.tm.MemberTm;
import lk.ijse.LibraSys.model.MemberModel;
import lk.ijse.LibraSys.model.MembershipFeeModel;
import lk.ijse.LibraSys.model.SignupModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberFormController {

    @FXML
    private JFXComboBox<String> cmbmembershipFeeId;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colMembername;

    @FXML
    private TableColumn<?, ?> colMid;

    @FXML
    private TableColumn<?, ?> colfeeId;

    @FXML
    private TableColumn<?, ?> colservicenum;

    @FXML
    private TableColumn<?, ?> coltelNumber;


    @FXML
    private Label lblPaidDate;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<MemberTm> tblMember;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtMid;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSnumber;

    @FXML
    private TextField txtTel;
    private MembershipFeeModel membershipFeeModel = new MembershipFeeModel();
    //private ObservableList<MemberTm> obList = FXCollections.observableArrayList();
    private MemberModel memberModel = new MemberModel();
    private SignupModel signupModel =new SignupModel();

    public  void initialize(){
        generateNextMemberId();
        loadFeeIds();
        loadAllMember();
        setCellValueFactory();
        tableListener();
    }

    private void tableListener() {
        tblMember.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);

        });
    }

    private void setData(MemberTm row) {
        txtMid.setText(row.getMid());
        txtName.setText(row.getName());
        txtAddress.setText(row.getAddress());
        txtGender.setText(row.getGender());
        txtTel.setText(row.getTel());
        cmbmembershipFeeId.setValue(row.getFeeId());
        txtSnumber.setText(row.getSNumber());
    }

    private void generateNextMemberId() {
        try {
            String mid = memberModel.generateNextMemberId(txtMid.getText());
            txtMid.setText(mid);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colMid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colMembername.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        coltelNumber.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colfeeId.setCellValueFactory(new PropertyValueFactory<>("feeId"));
        colservicenum.setCellValueFactory(new PropertyValueFactory<>("sNumber"));
    }

    private void loadAllMember() {
        //var model =new MemberModel();
        ObservableList<MemberTm> obList = FXCollections.observableArrayList();

        try {
            List<MemberDto> memberList = memberModel.getAllMember();

            for (MemberDto dto : memberList){
                obList.add(new MemberTm(
                        dto.getMid(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getGender(),
                        dto.getTel(),
                        dto.getFeeId(),
                        dto.getsNumber()
                ));

            }
            tblMember.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadFeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<MembershipFeeDto> idList = membershipFeeModel.getAllMemberShipFee();

            for (MembershipFeeDto dto : idList ) {
                obList.add(dto.getId());
            }
            cmbmembershipFeeId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();
        generateNextMemberId();
    }

    private void clearFields() {
        cmbmembershipFeeId.setValue("");
        txtSnumber.setText("");
        txtMid.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtGender.setText("");
        txtTel.setText("");

    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String mid = txtMid.getText();

        try {
            MemberDto dto = memberModel.searchMember(mid);
            if (dto != null){
                txtMid.setText(dto.getMid());
                txtName.setText(dto.getName());
                txtAddress.setText(dto.getAddress());
                txtGender.setText(dto.getGender());
                txtTel.setText(dto.getTel());
                cmbmembershipFeeId.setValue(dto.getFeeId());
                txtSnumber.setText(dto.getsNumber());
            }else {
                new Alert(Alert.AlertType.ERROR,"Member not found!!!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String mid  = txtMid.getText();

        try {
            boolean isDeleted = memberModel.deleteMember(mid);
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"member deleted successfully!!!").show();
                loadAllMember();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnNewMembershipFeeOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/membershipFee_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Membership Fee Form");
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isValidate = validateMember();
        if (isValidate){
            String mid  = txtMid.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String gender = txtGender.getText();
            String tel = txtTel.getText();
            String feeId = cmbmembershipFeeId.getValue();
            String sNumber = txtSnumber.getText();


            var dto = new MemberDto(mid,name,address,gender,tel,feeId,sNumber);

            try {
                boolean isSaved = memberModel.saveMember(dto);

                if(isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"member registered successfully!!!!!!").show();
                    clearFields();
                    loadAllMember();
                    setCellValueFactory();
                    generateNextMemberId();
                }else{
                    new Alert(Alert.AlertType.ERROR,"member registration failed!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }

    }

    private  boolean validateMember(){
        String mid = txtMid.getText();
        Pattern compile = Pattern.compile("[M][0-9]{3,}");
        Matcher matcher = compile.matcher(mid);
        boolean matches = matcher.matches();
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"Invalid member id").show();
            return  false;
        }

        String name =txtName.getText();
        boolean matches1 = Pattern.matches("[A-Za-z]{3,}", name);
        if (!matches1){
            new Alert(Alert.AlertType.ERROR,"Invalid member name!!!").show();
            return  false;
        }

        String address = txtAddress.getText();
        boolean matches2 = Pattern.matches("[0-9]{1,}\\/[A-Z]\\s[a-zA-Z]+$",address);
        if(!matches2){
            new Alert(Alert.AlertType.ERROR,"Invalid address!!!").show();
            return  false;
        }

        String gender = txtGender.getText();
        boolean matches3 = Pattern.matches("Female|Male",gender);
        if(!matches3){
            new Alert(Alert.AlertType.ERROR,"Gender type invalid!!!").show();
            return  false;
        }

        String tel = txtTel.getText();
        boolean matches4 = Pattern.matches("(070|071|072|074|075|076|077|078|027|066)\\d{7}",tel);
        if(!matches4){
            new Alert(Alert.AlertType.ERROR,"Mobile number Invalid!!!").show();
            return  false;
        }

       return  true;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String mid  = txtMid.getText();
        String name =  txtName.getText();
        String address = txtAddress.getText();
        String gender = txtGender.getText();
        String tel = txtTel.getText();
        String feeId = cmbmembershipFeeId.getValue();
        String sNumber = txtSnumber.getText();


        var dto = new MemberDto(mid,name,address,gender,tel,feeId,sNumber);

        try {
            boolean isUpdated = memberModel.updateMember(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"member updated successfully!!!").show();
                clearFields();
                loadAllMember();
            }else{
                new  Alert(Alert.AlertType.ERROR,"not updated!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbMembershipFeeOnAction(ActionEvent event) {
        String id = String.valueOf(cmbmembershipFeeId.getValue());
        try {
            MembershipFeeDto membershipFeeDto = membershipFeeModel.searchMembershipFee(id);
            lblPaidDate.setText(String.valueOf(membershipFeeDto.getDate()));
            txtName.setText(membershipFeeDto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
        stage.show();
    }

}

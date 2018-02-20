package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PrgState;
import model.Tuple;
import model.statements.IStmt;
import model.structures.*;
import mycontroller.MyController;
import repository.IRepository;
import repository.Repository;
import tableViews.BarrierTableView;
import tableViews.FileTableView;
import tableViews.HeapTableView;
import tableViews.SymTableView;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RunProgramController {

    //SYMBOL TABLE

    @FXML
    private TableView<SymTableView> symTable;
    @FXML
    private TableColumn<SymTableView, String> symTableVarName;
    @FXML
    private TableColumn<SymTableView, Integer> symTableValue;

    //HEAP TABLE
    @FXML
    private TableView<HeapTableView> heapTable;
    @FXML
    private TableView<BarrierTableView> barrierTable;

    @FXML
    private TableColumn<HeapTableView, Integer> heapAddressColumn;
    @FXML
    private TableColumn<HeapTableView, Integer> heapValueColumn;

    //FILE TABLE
    @FXML
    private TableView<FileTableView> fileTable;
    @FXML
    private TableColumn<FileTableView, Integer> fileTableIdentifier;
    @FXML
    private TableColumn<FileTableView, String> fileTableName;

    @FXML
    private ListView<String> outList;
    @FXML
    private ListView<String> exeStack;
    @FXML
    private ListView<String> prgStateIdentifiers;

    @FXML
    private Button oneStepButton;

    @FXML
    private TextField noPrgStateTextField;

    private Controller ctrl;

    @FXML
    public void initialize() {

        MyIStack<IStmt> stk = new MyStack<IStmt>();
        MyIDictionary<String, Integer> symtbl = new MyDictionary<String, Integer>();
        MyIList<Integer> out = new MyList<Integer>();
        MyIDictionary<Integer, Tuple<String, BufferedReader>> fileTable = new MyDictionary<Integer, Tuple<String, BufferedReader>>();
        MyIHeap<Integer> heap = new MyHeap<>();

        IRepository repo = new Repository("C://Users//Utilizator//Desktop//University//Year 2//MAP//lab11-extendedPractical//log.txt");
        IStmt stmt = mainWindowController.statement;
        PrgState programState = new PrgState(stk, symtbl, out, fileTable, stmt, heap);

        repo.add(programState);

        ctrl = new Controller(repo);

        this.heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        this.fileTableIdentifier.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.fileTableName.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        this.symTableVarName.setCellValueFactory(new PropertyValueFactory<>("varName"));
        this.symTableValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        setNoPrgState();
        setPrgStateIdentifiers();
        prgStateIdentifiers.getSelectionModel().select(0);
        setExeStack();

    }

    private void setNoPrgState() {
        Integer nr = ctrl.noPrgStates();
        noPrgStateTextField.setText(String.valueOf(nr));
    }

    private void setPrgStateIdentifiers() {
        prgStateIdentifiers.setItems(ctrl.getPrgStatesID());
    }

    PrgState getCurrentProgramState() {
        int index = prgStateIdentifiers.getSelectionModel().getSelectedIndex();
        if (index == -1)
            index = 0;
        return ctrl.getPrgStateByIndex(index);
    }


    private void setExeStack() {

        ObservableList<String> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();


        for (IStmt i : programState.getExeStack().getAll())
            list.add("" + i);

        exeStack.setItems(list);
    }

    private void setBarrierTable() {
        ObservableList<BarrierTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();

        for (Integer key : programState.getBarrierTable().getAll())

            try {
                list.add(new BarrierTableView(key, programState.getBarrierTable().lookUp(key).getItem2()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        barrierTable.setItems(list);

    }

    private void setHeapTable() {

        ObservableList<HeapTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState(); // here we don't need to get current because heap is shared by all
        // but i used to don't make another function to get one programState

        for (Integer key : programState.getHeap().getAll())

            try {
                list.add(new HeapTableView(key, programState.getHeap().get(key)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        heapTable.setItems(list);
    }

    private void setFileTable() {

        ObservableList<FileTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState(); // here we don't need to get current because fileTable is shared by all
        // but i used to don't make another function to get one programState

        for (Integer key : programState.getFileTable().getAllKeys())
            try {
                list.add(new FileTableView(key, programState.getFileTable().lookUp(key).getItem1()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        fileTable.setItems(list);
    }

    private void setSymTable() {

        ObservableList<SymTableView> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();

        for (String key : programState.getSymTable().getAll())
            try {
                list.add(new SymTableView(key, programState.getSymTable().lookUp(key)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        symTable.setItems(list);
    }

    private void setOutList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        PrgState programState = getCurrentProgramState();   // they all share the same outList

        for (Integer i : programState.getOut().getAll())
            list.add("" + i);

        outList.setItems(list);
    }

    private void setAll() {
        setNoPrgState();
        setPrgStateIdentifiers();
        setExeStack();
        setHeapTable();
        setFileTable();
        //setBarrierTable();
        setSymTable();
        setOutList();
    }

    public void executeOneStep() {
        try {
            if (ctrl.oneStepGUI()) {
                setAll();
            } else {
                setNoPrgState();
                setPrgStateIdentifiers();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        /*Node source = (Node) ae.getSource();
        Stage theStage = (Stage) source.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
        a.show();
        theStage.close();*/
        }
    }

    public void mouseClickPrgStateIdentifiers() {

        if (ctrl.noPrgStates() > 0)
            setAll();
    }
}

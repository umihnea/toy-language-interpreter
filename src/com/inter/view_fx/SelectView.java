package com.inter.view_fx;

import com.inter.model.expressions.ArithmeticExpression;
import com.inter.model.expressions.ConstantExpression;
import com.inter.model.expressions.ReadHeapExpression;
import com.inter.model.expressions.VariableExpression;
import com.inter.model.latch.CountDownStatement;
import com.inter.model.latch.LatchAwaitStatement;
import com.inter.model.latch.NewLatchStatement;
import com.inter.model.statements.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

public class SelectView {
    private Stage selectWindow;
    private Mediator mediator;

    public ListView<String> lstPrograms;
    public Button btnSelect;

    private static final ObservableList<String> programs = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        initData();
        lstPrograms.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lstPrograms.getSelectionModel().selectIndices(1);
        lstPrograms.setItems(programs);
    }

    private void initData() {
        programs.clear();
        for (int i = 1; i <= 10; i++)
            programs.add(String.valueOf(i));
    }

    public void setStage(Stage stage) {
        this.selectWindow = stage;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void selectProgram(ActionEvent actionEvent) {
        if (!lstPrograms.getSelectionModel().getSelectedItems().isEmpty()) {
            String selection = lstPrograms.getSelectionModel().getSelectedItems().get(0);
            mediator.makeSelection(
                    fetchStatement(Integer.parseInt(selection))
            );
        }
    }

    private Statement fetchStatement(int index) {
        switch (index) {
            case 1: {
                return new CompoundStatement(
                        new AssignmentStatement("a",
                                new ArithmeticExpression('+',
                                        new ConstantExpression(2),
                                        new ArithmeticExpression('*',
                                                new ConstantExpression(3),
                                                new ConstantExpression(5)
                                        )
                                )
                        ),
                        new CompoundStatement(
                                new AssignmentStatement("b",
                                        new ArithmeticExpression('+',
                                                new ArithmeticExpression('-',
                                                        new VariableExpression("a"),
                                                        new ArithmeticExpression('/',
                                                                new ConstantExpression(4),
                                                                new ConstantExpression(2)
                                                        )
                                                ),
                                                new ConstantExpression(7)
                                        )
                                ),
                                new PrintStatement(
                                        new VariableExpression("b")
                                )
                        )
                );
            }
            case 2: {
                /* a = 2 - 2;
                 * IF(a) THEN(
                 *    v = 2
                 * ) ELSE(
                 *    v = 3
                 * );
                 * PRINT(v) */
                return new CompoundStatement(
                        new AssignmentStatement(
                                "a",
                                new ArithmeticExpression('-',
                                        new ConstantExpression(2),
                                        new ConstantExpression(2)
                                )),
                        new CompoundStatement(
                                new IfStatement(
                                        new VariableExpression("a"),
                                        new AssignmentStatement("v", new ConstantExpression(2)),
                                        new AssignmentStatement("v", new ConstantExpression(3))
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                );
            }
            case 3:
                /* OPENRFILE(in_fd, "in.txt")
                 * READ(in_fd, a)
                 * PRINT(a)
                 * READ(in_fd, a)
                 * PRINT(a) */
                return new CompoundStatement(
                        new OpenRFileStatement("in_fd", "in.txt"),
                        new CompoundStatement(
                                new ReadFileStatement(new VariableExpression("in_fd"), "a"),
                                new CompoundStatement(
                                        new PrintStatement(new VariableExpression("a")),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("in_fd"), "a"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new CloseFileStatement(new VariableExpression("in_fd"))
                                                )
                                        )
                                )
                        )
                );
            case 4:
                /* v = 10;
                 * NEW(v, 20);
                 * NEW(a, 22);
                 * PRINT(v); */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                );
            case 5:
                /* v = 10;
                 * NEW(v, 20);
                 * NEW(a, 22);
                 * PRINT(100 + READ_HEAP(v));
                 * PRINT(100 + READ_HEAP(a)) */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new CompoundStatement(
                                                new PrintStatement(
                                                        new ArithmeticExpression('+',
                                                                new ConstantExpression(100),
                                                                new ReadHeapExpression("v")
                                                        )
                                                ),
                                                new PrintStatement(
                                                        new ArithmeticExpression('+',
                                                                new ConstantExpression(100),
                                                                new ReadHeapExpression("a")
                                                        )
                                                )
                                        )
                                )
                        )
                );
            case 6:
                /* v = 10;
                 * NEW(v,20); NEW(a,22);
                 * WRITE_HEAP(a, 100-70);
                 * PRINT(a);            // 2
                 * PRINT(READ_HEAP(a)); // 30
                 */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new CompoundStatement(
                                                new HeapWriteStatement("a",
                                                        new ArithmeticExpression('-',
                                                                new ConstantExpression(100),
                                                                new ConstantExpression(70)
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new PrintStatement(new ReadHeapExpression("a"))
                                                )
                                        )
                                )
                        )
                );
            case 7:
                /* v = 10;
                 * NEW(v,20); NEW(a,22);
                 * WRITE_HEAP(a, 30);
                 * PRINT(a);
                 * PRINT(READ_HEAP(a));
                 * a = 0;   // GC is called
                 * PRINT(READ_HEAP(a)); // fails
                 */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(
                                new HeapAllocStatement("v", new ConstantExpression(20)),
                                new CompoundStatement(
                                        new HeapAllocStatement("a", new ConstantExpression(22)),
                                        new CompoundStatement(
                                                new HeapWriteStatement("a",
                                                        new ConstantExpression(30)
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("a")),
                                                        new CompoundStatement(
                                                                new PrintStatement(new ReadHeapExpression("a")),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("a", new ConstantExpression(0)),
                                                                        new PrintStatement(new ReadHeapExpression("a"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );
            case 8:
                /* v = 6;
                 * WHILE (v-4) (
                 *    PRINT(v);
                 *    v = (v - 1);
                 * );
                 * PRINT(v);
                 */
                return new CompoundStatement(new AssignmentStatement("v", new ConstantExpression(6)),
                        new CompoundStatement(new WhileStatement(
                                new ArithmeticExpression('-',
                                        new VariableExpression("v"),
                                        new ConstantExpression(4)
                                ),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v",
                                                new ArithmeticExpression('-',
                                                        new VariableExpression("v"),
                                                        new ConstantExpression(1)
                                                ))
                                )
                        ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                );
            case 9:
                /*
                 * v = 10;
                 * new(a, 22);
                 * fork(
                 *   wH(a, 30);
                 *   v = 32;
                 *   print(v);
                 *   print(rH(a));
                 * );
                 * print(v);
                 * print(rH(a));
                 */
                return new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(10)),
                        new CompoundStatement(
                                new HeapAllocStatement("a", new ConstantExpression(22)),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new HeapWriteStatement("a", new ConstantExpression(30)),
                                                        new CompoundStatement(
                                                                new AssignmentStatement("v", new ConstantExpression(32)),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new ReadHeapExpression("a"))
                                                                )
                                                        )
                                                )
                                        ),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new ReadHeapExpression("a"))
                                        )
                                )
                        )
                );
            case 10:
                return new CompoundStatement(new HeapAllocStatement("v1", new ConstantExpression(2)),
                        new CompoundStatement(new HeapAllocStatement("v2", new ConstantExpression(3)),
                                new CompoundStatement(new HeapAllocStatement("v3", new ConstantExpression(4)),
                                        new CompoundStatement(new NewLatchStatement("cnt", new ReadHeapExpression("v2")),
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(new HeapWriteStatement("v1", new ArithmeticExpression('*', new ReadHeapExpression("v1"), new ConstantExpression(10))),
                                                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression("v1")), new CountDownStatement("cnt")))
                                                        ),
                                                        new CompoundStatement(
                                                                new ForkStatement(
                                                                        new CompoundStatement(new HeapWriteStatement("v2", new ArithmeticExpression('*', new ReadHeapExpression("v2"), new ConstantExpression(10))),
                                                                                new CompoundStatement(new PrintStatement(new ReadHeapExpression("v2")), new CountDownStatement("cnt")))
                                                                ),
                                                                new CompoundStatement(
                                                                        new ForkStatement(
                                                                                new CompoundStatement(new HeapWriteStatement("v3", new ArithmeticExpression('*', new ReadHeapExpression("v3"), new ConstantExpression(10))),
                                                                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression("v3")), new CountDownStatement("cnt")))
                                                                        ),
                                                                        new CompoundStatement(new LatchAwaitStatement("cnt"),
                                                                                new CompoundStatement(new PrintStatement(new ConstantExpression(100)),
                                                                                        new CompoundStatement(new CountDownStatement("cnt"), new PrintStatement(new ConstantExpression(100))
                                                                                        ))))))))));
            default:
                return null;
        }
    }

    public Stage getStage() {
        return selectWindow;
    }
}

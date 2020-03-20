import com.hp.gagawa.java.elements.*;

public class HTMLBuilder {
    private Table table = new Table();

    public void build() {
        table = new Table();
        addHeading();
        System.out.println(table.write());
    }

    private void addHeading() {
        Tr heading = new Tr();
        heading.appendChild(new Th().appendText("Matrix size").setRowspan("3"));
        heading.appendChild(new Th().appendText("Sequential algorithm").setRowspan("3"));
        heading.appendChild(new Th().appendText("Parallel algorithm").setRowspan("3"));

        table.appendChild(heading);

        Tr processorsNumber = new Tr();
        processorsNumber.appendChild(new Th().appendText("2 processes").setColspan("2"));
        processorsNumber.appendChild(new Th().appendText("4 processes").setColspan("2"));
        processorsNumber.appendChild(new Th().appendText("9 processes").setColspan("2"));

        table.appendChild(processorsNumber);

        Tr parameters = new Tr();

        for (int i = 0; i < 3; i++) {
            parameters.appendChild(new Th().appendText("Time"));
            parameters.appendChild(new Th().appendText("Acceleration"));
        }

        table.appendChild(parameters);
    }
}

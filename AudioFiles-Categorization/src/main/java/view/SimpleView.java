package view;

import controller.IController;
import model.IModel;
import model.simplemodel.SimpleModel;

public class SimpleView implements IView {

    MainForm form;

    public SimpleView(IModel model, IController controller) {
        this.form = new MainForm(model, controller);
    }

    @Override
    public void createView() {
        form.startSwingApplication();
    }
}

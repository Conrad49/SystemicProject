import javafx.scene.canvas.GraphicsContext;

public class ThingTwo extends Thing {

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(GraphicsContext g) {
        super.render(g);

        g.fillText("ThingTwo!", 100, 100);
    }
}

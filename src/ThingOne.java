import javafx.scene.canvas.GraphicsContext;

public class ThingOne extends Thing {

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(GraphicsContext g) {
        super.render(g);

        g.fillText("ThingOne!", 200, 200);
    }
}

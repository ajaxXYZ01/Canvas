package managers;

import ui.Inspector;
import viewport.Viewport2D;

public class WindowManager {
    
    private static Inspector inspector;
    private static Viewport2D viewport;
    private static Viewport2DElementManager current_scene;

    public static Viewport2D getViewport() {
        return viewport;
    }

    public static void setViewport(Viewport2D viewport) {
        WindowManager.viewport = viewport;
    }

    public static Inspector getInspector() {
        return inspector;
    }

    public static void setInspector(Inspector inspector) {
        WindowManager.inspector = inspector;
    }

    public static Viewport2DElementManager getCurrentScene() {
        return current_scene;
    }

    public static void setCurrentScene(Viewport2DElementManager scene) {
        current_scene = scene;
    }

}

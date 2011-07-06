package ua.kharkov.kture.ot.view.swing.additional;

import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.multivariants.VariantsEditWindow;
import ua.kharkov.kture.ot.view.swing.additional.optimization.OptimizationWindow;

/**
 * This class is used to show additional windows, like Optimization window or Variants window.
 * It does one of three thing:
 * 1. Create a window instance and show it if it was never called before
 * 2. Just show a window instance, if it was once called but then closed
 * 3. Bring window to front, if it wasn't closed before being called this time.
 *
 * @author alexander.korotkikh
 */
@Deprecated
public class AdditionalWindowManager {
    private static VariantsEditWindow variantsWindow;
    private static OptimizationWindow optimizationWindow;

    public static void getVariantsWindow() {
        if (variantsWindow == null) {
            variantsWindow = new VariantsEditWindow();
        }
        getWindow(variantsWindow);
    }

    public static void getOptimizationWindow() {
        if (optimizationWindow == null) {
            optimizationWindow = new OptimizationWindow();
        }
        getWindow(optimizationWindow);
    }

    private static void getWindow(AdditionalWindow window) {
        /*if (!window.isVisible()) {
            window.setVisible(true);
        }
        window.toFront();*/
    }
}

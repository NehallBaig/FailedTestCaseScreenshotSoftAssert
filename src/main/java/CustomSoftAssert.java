import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

public class CustomSoftAssert extends SoftAssert {

    // Custom SoftAssert class for capturing screenshot on each step fail
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        Utils.takeScreenshot();
    }

}

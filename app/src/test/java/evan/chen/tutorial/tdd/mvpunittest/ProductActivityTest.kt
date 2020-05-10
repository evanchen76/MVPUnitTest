package evan.chen.tutorial.tdd.mvpunittest

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ProductActivityTest {

    private lateinit var activity: ProductActivity

    @MockK
    private lateinit var presenter: ProductContract.IProductPresenter

    @Before
    fun setupActivity() {

        MockKAnnotations.init(this)

        val activityController = Robolectric.buildActivity(ProductActivity::class.java)
        activity = activityController.get()

    }

}

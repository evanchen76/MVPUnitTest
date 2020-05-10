package evan.chen.tutorial.tdd.mvpunittest

import evan.chen.tutorial.tdd.mvpunittest.api.ProductResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ProductPresenterTest {

    private lateinit var presenter: ProductContract.IProductPresenter
    private var productResponse = ProductResponse()

    @MockK(relaxed = true)
    private lateinit var repository: IProductRepository

    @MockK(relaxed = true)
    private lateinit var productView: ProductContract.IProductView

    @Before
    fun setupPresenter() {
        MockKAnnotations.init(this)

        presenter = ProductPresenter(productView, repository)

        productResponse.id = "pixel3"
        productResponse.name = "Google Pixel 3"
        productResponse.price = 27000
        productResponse.desc = "Desc"
    }

    @Test
    fun getProductTest() {
        val productId = "pixel3"

        val slot = slot<IProductRepository.LoadProductCallback>()

        //驗證是否有呼叫IProductRepository.getProduct
        every { repository.getProduct(eq(productId), capture(slot)) }
            .answers {
                //將callback攔截下載並指定productResponse的值。
                slot.captured.onProductResult(productResponse)
            }

        presenter.getProduct(productId)

        //驗證是否有呼叫View.onGetResult及是否傳入productResponse
        verify { productView.onGetResult(eq(productResponse)) }
    }

    @Test
    fun buySuccessTest() {
        val slot = slot<IProductRepository.BuyProductCallback>()

        val productId = "pixel3"
        val items = 3

        every { repository.buy(eq(productId), eq(items), capture(slot)) }
            .answers {
                slot.captured.onBuyResult(true)
            }

        presenter.buy(productId, items)

        verify { productView.onBuySuccess() }
    }

    @Test
    fun buyFailTest() {
        val slot = slot<IProductRepository.BuyProductCallback>()

        val productId = "pixel3"
        val items = 11


        every { repository.buy(eq(productId), eq(items), capture(slot)) }
            .answers {
                slot.captured.onBuyResult(false)
            }

        presenter.buy(productId, items)

        verify { productView.onBuyFail() }
    }
}
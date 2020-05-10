package evan.chen.tutorial.tdd.mvpunittest

import evan.chen.tutorial.tdd.mvpunittest.api.ProductResponse

class ProductPresenter(
    private val view: ProductContract.IProductView,
    private val productRepository: IProductRepository
) : ProductContract.IProductPresenter {

    override fun buy(productId: String, numbers: Int) {
        productRepository.buy(productId, numbers, object : IProductRepository.BuyProductCallback {
            override fun onBuyResult(isSuccess: Boolean) {
                if (isSuccess) {
                    view.onBuySuccess()
                } else {
                    view.onBuyFail()
                }
            }

        })
    }

    override fun getProduct(productId: String) {
        productRepository.getProduct(productId, object : IProductRepository.LoadProductCallback {
            override fun onProductResult(productResponse: ProductResponse) {
                view.onGetResult(productResponse)
            }
        })
    }
}
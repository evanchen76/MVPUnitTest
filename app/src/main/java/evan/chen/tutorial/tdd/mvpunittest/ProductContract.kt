package evan.chen.tutorial.tdd.mvpunittest

import evan.chen.tutorial.tdd.mvpunittest.api.ProductResponse

class ProductContract {

    interface IProductPresenter {
        fun getProduct(productId: String)
        fun buy(productId: String, numbers: Int)
    }

    interface IProductView {
        fun onGetResult(productResponse: ProductResponse)
        fun onBuySuccess()
        fun onBuyFail()

    }
}


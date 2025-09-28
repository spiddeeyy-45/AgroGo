package AdapterClass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {
    private val _paymentResult = MutableLiveData<eventpayment<Pair<Boolean, String?>>>()
    val paymentResult: LiveData<eventpayment<Pair<Boolean, String?>>> get() = _paymentResult

    fun setPaymentResult(success: Boolean, paymentId: String?) {
        _paymentResult.value = eventpayment(success to paymentId)
    }
    private val _selectedProduct = MutableLiveData<Any?>()
    val selectedProduct: LiveData<Any?> get() = _selectedProduct

    fun setSelectedProduct(product: Any) {
        _selectedProduct.value = product
    }
}
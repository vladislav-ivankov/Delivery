function calculateSubtotal(input) {
    var price = input.parentNode.parentNode.querySelector('.product-price').textContent.replace(' $', '');
    var quantity = input.value;
    var subtotal = price * quantity;
    input.parentNode.parentNode.querySelector('.product-subtotal span').textContent = subtotal.toFixed(2) + ' $';

    var total = 0;
    var subtotals = document.querySelectorAll('.product-subtotal span');
    for (var i = 0; i < subtotals.length; i++) {
        var subtotal = parseFloat(subtotals[i].textContent.replace(' $', ''));
        if (!isNaN(subtotal)) {
            total += subtotal;
        }
    }
    document.querySelector('.total-value').textContent = total.toFixed(2) + ' $';
    document.getElementById(`quantity-${productId}`).value = quantity;
}

var quantityInputs = document.querySelectorAll('.product-quantity input');
for (var i = 0; i < quantityInputs.length; i++) {
    quantityInputs[i].addEventListener('change', function() {
        calculateSubtotal(this);
    });
}




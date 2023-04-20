document.addEventListener("DOMContentLoaded", function () {
    var paymentOptions = document.getElementsByName('payment');
    var cardDetails = document.getElementById('card-details');

    for (var i = 0; i < paymentOptions.length; i++) {
        paymentOptions[i].addEventListener('change', function () {
            if (this.value == 'card-now') {
                cardDetails.style.display = 'block';
                cardDetails.required = true;
            } else {
                cardDetails.style.display = 'none';
                cardDetails.required = false;
            }
        });
    }

    document.querySelector('form').addEventListener('submit', function (e) {
        var paymentOptions = document.getElementsByName('payment');
        var cardDetails = document.getElementById('card-details');
        var cardNumber = document.getElementById('card-number').value;
        var cardHolder = document.getElementById('card-holder').value;
        var cardExpiry = document.getElementById('card-expiry').value;
        var cardCvv = document.getElementById('card-cvv').value;

        for (var i = 0; i < paymentOptions.length; i++) {
            if (paymentOptions[i].checked && paymentOptions[i].value === 'card-now') {
                if (cardNumber === '' || cardHolder === '' || cardExpiry === '' || cardCvv === '') {
                    e.preventDefault();
                    alert('Please fill in all card details.');
                }
            }
        }
    });
});
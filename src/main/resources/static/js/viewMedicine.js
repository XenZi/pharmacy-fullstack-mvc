const addToCartBtnView = document.querySelectorAll('.add-to-cart-btn-view');

addToCartBtnView.forEach(cartBtn => {
    cartBtn.addEventListener('click', (e) => {
        e.preventDefault();
        const medicineID = cartBtn.getAttribute("data-medicine_id");
        const quantity = document.querySelector('input[name="quantityForCart"]').value;
        let formData = new FormData();
        formData.append("medicineID", medicineID);
        formData.append("quantity", quantity );
        fetch(`http://localhost:8080/cart/add`, {
            method: 'POST',
            body: formData
        }).then(res => res.json())
            .catch(err => {
                cart.textContent = 'Error while adding!';
                setTimeout(() => {
                    cart.textContent = 'Add to cart';
                }, 1000);
                return;
            })
            .then(data => {
                cart.textContent = 'Added to cart!';
                setTimeout(() => {
                    cart.textContent = 'Add to cart';
                }, 1000);
                return;
            })

    })
});


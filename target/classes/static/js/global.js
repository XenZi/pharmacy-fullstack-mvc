const allWishListButtons = document.querySelectorAll(".wish-list-button");
const allShoppingCartIndexButtons = document.querySelectorAll(".shopping-cart-button-index");


const addToCart = (cart) => {
    const medicineID = cart.getAttribute("data-medicine_id");
    let formData = new FormData();
    formData.append("medicineID", medicineID);
    formData.append("quantity", 1);
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
        });
};


allWishListButtons.forEach(wishList => {
    wishList.addEventListener('click', (e) => {
        e.preventDefault();
        const medicineID = wishList.getAttribute("data-medicine_id");
        const userID = wishList.getAttribute("data-user_list");
        if (userID == null) {
            return;
        }
        fetch(`http://localhost:8080/wish-list/add?medicineName=${medicineID}`,{
            method: 'GET'
        }).then((res) => {res.json()})
            .then(data => {
                wishList.textContent = "Added to Wish List";
                wishList.classList.add("btn-success");

                setTimeout(() => {
                    wishList.textContent = "Add to Wish List";
                    wishList.classList.remove("btn-success");
                }, 1000)
                wishList.classList.remove("btn-success")
            })
            .catch(error => console.log(error));
    })
});


allShoppingCartIndexButtons.forEach(cart => {
    cart.addEventListener('click', (e) => {
        e.preventDefault();
        addToCart(cart);
    });
})



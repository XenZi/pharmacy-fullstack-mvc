const itemInputs = document.querySelectorAll('.item-input');
const btnRaiseQuantity = document.querySelectorAll('.btn-raise-quantity');
const btnLowerQuantity = document.querySelectorAll('.btn-lower-quantity');
const totalPriceHolders = document.querySelectorAll('.total-price');
const loyaltyCardInput = document.querySelector('#loyaltyCardInput');
const checkout = document.querySelector('.btn-checkout');


const getTotalPriceWithLoyaltyCard = () => {
    fetch(`http://localhost:8080/cart/calculate-price?points=${loyaltyCardInput.value}`, {
        method: 'GET'
    }).then(res => res.text()).then(data => totalPriceHolders[1].textContent = data);
}

checkout.addEventListener('click', (e) => {
    e.preventDefault();
    let formData = new FormData();
    const loyaltyCardPoints = loyaltyCardInput ? loyaltyCardInput.value : 0;
    formData.append('loyaltyCardPoints', Number(loyaltyCardPoints));
    fetch(`http://localhost:8080/cart/buy`,
        {
            method: 'POST',
            body: formData
        }).then(res => res.text()).then(data => {
        console.log(data)
        checkout.textContent = data;
        setTimeout(() => {
            window.location.replace('/');
        }, 1000);
    });

});

itemInputs.forEach(input => {
    input.addEventListener('change', () => {
        if (input.value < 1) {
            input.value = 1;
        }
        const medicineID = input.getAttribute("data-medicine_id");
        let formData = new FormData();
        formData.append('medicineID', medicineID);
        formData.append('quantity', input.value);
        fetch(`http://localhost:8080/cart/update`,
            {
                method: 'POST',
                body: formData
            }).then(res => res.text()).then(data => {
            totalPriceHolders.forEach(holder => {
                holder.textContent = data;
            });
            if (loyaltyCardInput.value) {
                getTotalPriceWithLoyaltyCard();
            }
        }).catch(err => console.log(err));
    });
});


loyaltyCardInput?.addEventListener('change', (e) => {
    if (loyaltyCardInput.value <= 0) {
        loyaltyCardInput.value = 0;
    }

    if (loyaltyCardInput.value > 10) {
        loyaltyCardInput.value = 10;
    }

    getTotalPriceWithLoyaltyCard();
})



btnRaiseQuantity.forEach(btn => {
    btn.addEventListener('click', (e) => {
        e.preventDefault();
        const inputField = btn.parentElement.querySelector('input[type=number]');
        let inputFieldValue = Number(inputField.value) + 1;
        inputField.value = inputFieldValue;
        const medicineID = inputField.getAttribute("data-medicine_id");
        let formData = new FormData();
        formData.append('medicineID', medicineID);
        formData.append('quantity', inputFieldValue);
        fetch(`http://localhost:8080/cart/update`,
            {
                method: 'POST',
                body: formData
            }).then(res => res.text()).then(data => {
            totalPriceHolders.forEach(holder => {
                holder.textContent = data;
            });
            if (loyaltyCardInput?.value) {
                getTotalPriceWithLoyaltyCard();
            }
        }).catch(err => console.log(err));
    })
})



btnLowerQuantity.forEach(btn => {
    btn.addEventListener('click', (e) => {
        e.preventDefault();
        const inputField = btn.parentElement.querySelector('input[type=number]');
        let inputFieldValue = Number(inputField.value) - 1;
        if (inputFieldValue <= 1) {
            inputFieldValue = 1;
        }
        inputField.value = inputFieldValue;
        const medicineID = inputField.getAttribute("data-medicine_id");
        let formData = new FormData();
        formData.append('medicineID', medicineID);
        formData.append('quantity', inputFieldValue);
        fetch(`http://localhost:8080/cart/update`,
            {
                method: 'POST',
                body: formData
            }).then(res => res.text()).then(data => {
            totalPriceHolders.forEach(holder => {
                holder.textContent = data;
            });
        }).catch(err => console.log(err));
    })
})
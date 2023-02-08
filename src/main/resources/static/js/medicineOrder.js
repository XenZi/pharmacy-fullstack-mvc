

const selectWithElements = document.querySelector('.medicine-select');
const orderRowComponent = document.querySelector(".order-row");
const btnAddNewRow = document.querySelector(".btn-add-new-order");
const btnSendOrder = document.querySelector('.btn-send-order');
const btnUpdateOrder = document.querySelector('.btn-update-order');
const btnUpdateOrderFeedback = document.querySelector('.btn-update-order-feedback');
const btnRejectOrder = document.querySelector('.btn-delete-order-feedback');
const btnApproveOrder = document.querySelector('.btn-approve-order');

const template = `
<div class="form-floating mb-3">
  <textarea
    class="form-control"
    placeholder="Leave a comment here"
    id="floatingTextarea"
    name="description"
  ></textarea>
  <label for="floatingTextarea">Description</label>
</div>
<div class="form-floating mb-3">
  <input
    type="number"
    class="form-control"
    id="exampleFormControlInput1"
    placeholder="Quantity"
    name="quantity"
  />
  <label for="exampleFormControlInput1">Quantity</label>
</div>
</div>
`;

btnAddNewRow?.addEventListener("click", () => {
    const arr = [...selectWithElements.children];
    const createdArrWithEl = arr.map(el => {
        return {
            text: el.text,
            value: el.value
        }
    });
    const newDiv = document.createElement("div");
    const select = document.createElement("select");
    const optionsArr = createdArrWithEl.map(el => {
        const option = document.createElement("option");
        option.setAttribute("value", el.value);
        option.innerText = el.text;
        return option;
    });
    optionsArr[0].removeAttribute("value");
    optionsArr[0].setAttribute("selected", "selected");
    optionsArr[0].setAttribute("disabled", "disabled");
    optionsArr.forEach(el => select.appendChild(el));
    select.classList.add("medicine-select","form-select", "mb-3");
    newDiv.classList.add("group", "border", "p-3", "mt-3");
    newDiv.appendChild(select);
    newDiv.innerHTML = newDiv.innerHTML + template;
    orderRowComponent.appendChild(newDiv);
});

btnSendOrder?.addEventListener('click', () => {
    const getAllGroups = document.querySelectorAll('.group');
    const orders = [];
   getAllGroups.forEach(group => {
      const description = group.querySelector('textarea[name="description"]').value;
      const medicineID = group.querySelector('select').value;
      const quantity = group.querySelector('input[name="quantity"]').value;
      if (medicineID == 'Pick your medicine' || !medicineID) {
          alert('Please select medicine!');
          return;
      }
      const orderObj = {
          medicineID: medicineID,
          description: description,
          quantity: Number(quantity)
      };
      orders.push(orderObj);
   });
   fetch('http://localhost:8080/order/createOrder', {
       method: "POST",
       headers: {
           'Content-Type': 'application/json'
       },
       body: JSON.stringify(orders)
   }).then(res => res.text()).then(data => {
      if (data == 'Order created!') {
          alert("Successfully!")
          window.location.replace('/pharmacist');
      } else {
          alert("Error!");
      }
   });
});
const getParamValue = () => {
    const location = decodeURI(window.location.toString());
    const index = location.indexOf("?") + 1;
    const substrings = location.substring(index, location.length);
    const splitedSubstring = substrings.split("=");
    console.log(splitedSubstring);
    return splitedSubstring[1]
}

btnUpdateOrder?.addEventListener('click', () => {
    const getAllGroups = document.querySelectorAll('.group');
    const orders = [];
    let orderID = getParamValue();

    getAllGroups.forEach(group => {
        const description = group.querySelector('textarea[name="description"]').value;
        const medicineID = group.querySelector('.medicine-select').value;
        const quantity = group.querySelector('input[name="quantity"]').value;
        const id = group.getAttribute("id");
        if (medicineID == 'Pick your medicine' || !medicineID) {
            alert('Please select medicine!');
            return;
        }
        const orderObj = {
            id: Number(id),
            medicineID: medicineID,
            description: description,
            quantity: Number(quantity),
            orderID: orderID,
        };
        orders.push(orderObj);
    });
    console.log(orders);
    fetch('http://localhost:8080/order/update', {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orders)
    }).then(res => res.text()).then(data => {
        if (data == 'Order updated!') {
            alert("Successfully")
            window.location.replace('/pharmacist');
        } else {
            alert("Error!");
        }
    });
});


btnUpdateOrderFeedback?.addEventListener('click', () => {
    const feedback = document.querySelector('.feedback').value;
    let orderID = getParamValue();
    let formData = new FormData();
    formData.append('feedback', feedback);
    formData.append('orderID', orderID);
    fetch('http://localhost:8080/order/updateFeedback', {
        method: "POST",
        body: formData
    }).then(res => res.text()).then(data => console.log(data));
});

btnRejectOrder?.addEventListener('click', () => {
   const feedback = document.querySelector('.feedback').value;
   let orderID = getParamValue();
    let formData = new FormData();
    formData.append('feedback', feedback);
    formData.append('orderID', orderID);
    fetch('http://localhost:8080/order/reject', {
        method: "POST",
        body: formData
    }).then(res => res.text()).then(data => console.log(data));
});

btnApproveOrder?.addEventListener('click', () => {
    let orderID = getParamValue();
    let formData = new FormData();
    formData.append('orderID', orderID);
    fetch('http://localhost:8080/order/approve', {
        method: "POST",
        body: formData
    }).then(res => res.text()).then(data => console.log(data));
});
const btnSearch = document.querySelector('.btn-search');



let rowCardAppendEl = document.querySelector('.medicine-row-append');
btnSearch.addEventListener('click', (e) => {
    // const formSearch = document.querySelector('.form-search');
    // const medicineName = formSearch.querySelector('input[name=medicineName]').value;
    // const categorySelect = formSearch.querySelector("select[name=categoryID]").value;
    // const priceFrom = formSearch.querySelector('input[name=priceFrom]').value;
    // const priceTo = formSearch.querySelector('input[name=priceTo]').value;
    // const manufacturerSelect = formSearch.querySelector('select[name=manufacturerID]').value;
    //
    // let sendingRequest = {};
    // if (medicineName != "") {
    //     sendingRequest = {
    //         ...sendingRequest,
    //         medicineName: medicineName
    //     }
    // }
    // if (categorySelect != -1) {
    //     sendingRequest = {
    //         ...sendingRequest,
    //         categoryID: categorySelect
    //     }
    // }
    // if (priceFrom != "") {
    //     sendingRequest = {
    //         ...sendingRequest,
    //         priceFrom: priceFrom
    //     }
    // }
    // if (priceTo != "") {
    //     sendingRequest = {
    //         ...sendingRequest,
    //         priceTo: priceTo
    //     }
    // }
    // if (manufacturerSelect != -1) {
    //     sendingRequest = {
    //         ...sendingRequest,
    //         manufacturerID: manufacturerSelect
    //     }
    // }
    //
    // // http://localhost:8080/search?medicineName=&categoryID=-1&priceFrom=&priceTo=&manufacturerID=-1
    // let baseURL = `http://localhost:8080/search?`;
    // for (const key in sendingRequest) {
    //     baseURL += `${key}=${sendingRequest[key]}&`
    // }
    // fetch(baseURL,
    //     {
    //         method: "GET",
    //     })
    //     .then(res => res.json())
    //     .then(data => {
    //         rowCardAppendEl.innerHTML = "";
    //         const arrElements = [...data];
    //         arrElements.forEach(el => {
    //            rowCardAppendEl.innerHTML +=
    //            `
    //            <div class="row flex-column rounded align-center justify-content-center"
    //             >
    //               <div class="col-12 text-center">
    //                 <img
    //                         src="http://localhost:8080/uploads/images?imageName=${el.picture}"
    //                         class="img-thumbnail"
    //                 />
    //               </div>
    //               <div class="col-12 mt-3 d-flex justify-content-between">
    //                 <a href="medicine/view?id=${el.id}|" text="${el.title}"></a>
    //                 <p text="${el.price}"></p>
    //               </div>
    //               <div class="col-12 mb-3 d-flex justify-content-end">
    //                 <p text="${el.avgRating}"></p>
    //               </div>
    //               <div class="col-12 mb-3 d-flex justify-content-between">
    //                 <btn class="btn btn-danger wish-list-button" data-medicine_id="${medicine.getId()}" data-user_list=${session.user?.getWishList()?.getId()}" th:text="#{index_card.wish}"></btn>
    //                 <button class="btn btn-primary shopping-cart-button-index" th:text="#{index_card.buy}" th:attr="data-medicine_id=${medicine.getId()}"></button>
    //               </div>
    //         </div>
    //            `
    //         });
    //     })
});



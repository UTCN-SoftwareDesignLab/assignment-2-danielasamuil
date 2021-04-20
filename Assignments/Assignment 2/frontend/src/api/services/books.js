import authHeader, {BASE_URL, HTTP} from "../http";

export default {
    allBooks() {
        return HTTP.get(BASE_URL + "/books", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(book) {
        return HTTP.post(BASE_URL + "/books", book, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    edit(id, book) {
        console.log("id: " + id)
        return HTTP.put(BASE_URL + "/books/" + id, book, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    delete(id) {
        console.log("id: " + id)
        return HTTP.delete(BASE_URL + "/books/" + id, {headers: authHeader()}).then(
            () => {
                return true;
            }
        );
    },
    deleteAll() {
        return HTTP.delete(BASE_URL + "/books", {headers: authHeader()}).then(
            () => {
                return true;
            }
        );
    },
    csv() {
        return HTTP.get(BASE_URL + "/books/export/CSV", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    pdf() {
        return HTTP.get(BASE_URL + "/books/export/PDF", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    sell(id) {
        console.log("id: " + id)
        return HTTP.patch(BASE_URL + "/books/", id, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
    search() {
        return HTTP.get(BASE_URL + "/books/specific-books", {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
};

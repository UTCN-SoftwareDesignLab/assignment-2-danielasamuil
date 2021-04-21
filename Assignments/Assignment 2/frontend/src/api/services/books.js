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
    edit(book) {
        return HTTP.put(BASE_URL + "/books/" + book.id, book, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    delete(book) {
        return HTTP.delete(BASE_URL + "/books/" + book.id, {headers: authHeader()}).then(
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
    sell(book) {
        return HTTP.patch(BASE_URL + "/books/", book.id, {
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

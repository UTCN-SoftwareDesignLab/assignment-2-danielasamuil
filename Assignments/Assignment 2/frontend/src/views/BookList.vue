<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search Book"
          single-line
          hide-details
      ></v-text-field>
      <v-btn @click="addBook">Add Book</v-btn>
      <v-btn @click="exportCSV">CSV Report</v-btn>
      <v-btn @click="exportPDF">PDF Report</v-btn>
      <v-btn @click="sell">Sell book</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="items"
        :search="search"
        @click:row="editBook"
    ></v-data-table>
    <BookDialog
        :opened="dialogVisible"
        :book="selectedBook"
        @refresh="refreshList"
    ></BookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";

export default {
  name: "BookList",
  components: {BookDialog},
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "name",
        },
        {
          text: "Author", value: "author"
        },
        {
          text: "Genre", value: "genre"
        },
        {
          text: "Quantity", value: "quantity"
        },
        {
          text: "Price", value: "price"
        },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    editBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    addBook() {
      this.dialogVisible = true;
    },
    exportCSV() {
      api.books.csv();
    },
    exportPDF() {
      api.books.pdf();
    },
    sell(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>

package handler

import "github.com/gorilla/mux"

func SetupRoutes(handler *Handler) *mux.Router {
	r := mux.NewRouter()

	r.HandleFunc("/v1/sms/user/{phone}", handler.GetUserMessages).
		Methods("GET")

	return r
}

package handler

import (
	"encoding/json"
	"net/http"

	"github.com/gorilla/mux"

	"sms-consumer-go/internal/service"
)

type Handler struct {
	svc *service.SmsService
}

func NewHandler(svc *service.SmsService) *Handler {
	return &Handler{svc}
}
func (h *Handler) GetUserMessages(w http.ResponseWriter, r *http.Request) {
	phone := mux.Vars(r)["phone"]

	msgs, err := h.svc.GetMessagesByPhone(r.Context(), phone)
	if err != nil {
		http.Error(w, "Failed to fetch messages", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(msgs)
}

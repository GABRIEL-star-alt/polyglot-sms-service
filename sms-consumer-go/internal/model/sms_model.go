package model

type SmsEvent struct {
	Phone   string `json:"phone" bson:"phone"`
	Message string `json:"message" bson:"message"`
	Status  string `json:"status" bson:"status"`
}

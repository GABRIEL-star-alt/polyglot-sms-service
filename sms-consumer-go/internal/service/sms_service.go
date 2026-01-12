package service

import (
	"context"

	"sms-consumer-go/internal/model"
	mon "sms-consumer-go/mongo"
)

type SmsService struct {
	repo mon.SmsRepository
}

func NewSmsService(repo mon.SmsRepository) *SmsService {
	return &SmsService{repo: repo}
}

func (s *SmsService) GetMessagesByPhone(ctx context.Context, phone string) ([]model.SmsEvent, error) {
	return s.repo.FindByPhone(ctx, phone)
}

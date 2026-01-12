package service

import (
	"context"
	"errors"
	"testing"

	"sms-consumer-go/internal/model"
)

type MockSmsRepository struct {
	FindByPhoneFn func(ctx context.Context, phone string) ([]model.SmsEvent, error)
	SaveFn        func(ctx context.Context, event model.SmsEvent) error
}

func (m *MockSmsRepository) FindByPhone(
	ctx context.Context,
	phone string,
) ([]model.SmsEvent, error) {
	return m.FindByPhoneFn(ctx, phone)
}

func (m *MockSmsRepository) Save(
	ctx context.Context,
	event model.SmsEvent,
) error {
	if m.SaveFn == nil {
		return nil
	}
	return m.SaveFn(ctx, event)
}

func TestGetMessagesByPhone_Success(t *testing.T) {
	ctx := context.Background()
	phone := "999"

	expected := []model.SmsEvent{
		{Phone: "999", Message: "hello", Status: "SUCCESS"},
		{Phone: "999", Message: "hi", Status: "FAILED"},
	}

	mockRepo := &MockSmsRepository{
		FindByPhoneFn: func(ctx context.Context, phone string) ([]model.SmsEvent, error) {
			return expected, nil
		},
	}

	service := NewSmsService(mockRepo)

	result, err := service.GetMessagesByPhone(ctx, phone)

	if err != nil {
		t.Fatalf("expected no error, got %v", err)
	}

	if len(result) != len(expected) {
		t.Fatalf("expected %d events, got %d", len(expected), len(result))
	}
}

func TestGetMessagesByPhone_Error(t *testing.T) {
	ctx := context.Background()
	phone := "999"

	expectedErr := errors.New("db failure")

	mockRepo := &MockSmsRepository{
		FindByPhoneFn: func(ctx context.Context, phone string) ([]model.SmsEvent, error) {
			return nil, expectedErr
		},
	}

	service := NewSmsService(mockRepo)

	_, err := service.GetMessagesByPhone(ctx, phone)

	if err == nil {
		t.Fatal("expected error, got nil")
	}
}

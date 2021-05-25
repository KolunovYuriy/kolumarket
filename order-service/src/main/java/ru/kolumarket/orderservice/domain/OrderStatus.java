package ru.kolumarket.orderservice.domain;

public enum OrderStatus {
    CREATE {
        @Override
        public String toString() {
            return "CREATE";
        }
    }
    , PAID, DELIVERED, CANCELED;

}

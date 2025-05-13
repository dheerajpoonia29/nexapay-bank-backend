package com.nexapay.nexapay_bank_backend.model;

import com.nexapay.dto.request.TransactionRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "transaction_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Long transactionId;

    @Column(nullable = false)
    private String fromAccountNo;

    @Column(nullable = false)
    private String toAccountNo;

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp date;

    @Column
    private Boolean status;

    @Column
    private String statusInfo;

    public static TransactionEntity toEntity(TransactionRequest request) {
        return TransactionEntity.builder()
                .fromAccountNo(request.getFromAccountNo())
                .toAccountNo(request.getToAccountNo())
                .amount(request.getAmount())
                .build();
    }
}

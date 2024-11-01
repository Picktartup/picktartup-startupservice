package com.picktartup.startup.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Table(name = "wallet")
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq_generator")
    @SequenceGenerator(name = "wallet_seq_generator", sequenceName = "wallet_seq", allocationSize = 1)
    @Column(name = "wallet_id")
    private Long walletId;

    private String address;
    private Double balance;


}

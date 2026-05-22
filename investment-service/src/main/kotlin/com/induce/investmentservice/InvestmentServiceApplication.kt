package com.induce.investmentservice

import com.induce.cbrservice.grpc.CbrGrpcServiceGrpc
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.grpc.client.ImportGrpcClients

@SpringBootApplication
@ImportGrpcClients(basePackageClasses = [CbrGrpcServiceGrpc::class])
class InvestmentServiceApplication

fun main(args: Array<String>) {
    runApplication<InvestmentServiceApplication>(*args)
}

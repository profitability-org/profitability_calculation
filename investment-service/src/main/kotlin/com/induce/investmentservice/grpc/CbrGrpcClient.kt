package com.induce.investmentservice.grpc

import com.induce.cbrservice.grpc.CbrGrpcServiceGrpc
import com.induce.cbrservice.grpc.InflationRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CbrGrpcClient(
    private val cbrGrpcServiceBlockingStub: CbrGrpcServiceGrpc.CbrGrpcServiceBlockingStub
) {
    private val logger = LoggerFactory.getLogger(CbrGrpcClient::class.java)

    fun fetchCurrentInflationRate(): BigDecimal {
        try {
            logger.info("Sending gRPC request to cbr-service for inflation rate")

            val grpcRequest = InflationRequest.newBuilder().build()
            val response = cbrGrpcServiceBlockingStub.getInflationRate(grpcRequest)

            logger.info("Received inflation rate from cbr-service: ${response.inflationRate}")

            return BigDecimal(response.inflationRate)

        } catch (e: Exception) {
            logger.error("Error during gRPC call to cbr-service: ${e.message}", e)
            throw IllegalStateException("Failed to retrieve inflation data from CBR service: ${e.message}", e)
        }
    }
}

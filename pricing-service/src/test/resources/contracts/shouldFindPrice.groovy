import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "this is test contract"

    request {
        method 'GET'
        url '/api/price/A_1'
    }

    response {
        status 200
        headers {
            header("Content-Type", "application/json;charset=UTF-8")
        }
        body("""
      {
        "id": "A_1",
        "price": "338.33"
      }
      """)
    }
}
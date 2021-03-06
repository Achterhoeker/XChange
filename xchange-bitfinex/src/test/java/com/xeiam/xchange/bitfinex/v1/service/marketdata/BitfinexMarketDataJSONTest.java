/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.bitfinex.v1.service.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexDepth;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexLendDepth;

public class BitfinexMarketDataJSONTest {

  @Test
  public void testLendbookMarketData() throws IOException {
    
    InputStream resourceAsStream = BitfinexMarketDataJSONTest.class.getResourceAsStream("/v1/marketdata/example-marketdepth-lendbook-data.json");
    BitfinexLendDepth lendDepth = new ObjectMapper().readValue(resourceAsStream, BitfinexLendDepth.class);
    
    assertEquals(lendDepth.getAsks().length, 50);
    assertEquals(lendDepth.getBids().length, 50);
  }

  @Test
  public void testMarketDepth() throws Exception {
    InputStream resourceAsStream = BitfinexMarketDataJSONTest.class.getResourceAsStream("/v1/marketdata/example-marketdepth-data.json");
    BitfinexDepth depthRaw = new ObjectMapper().readValue(resourceAsStream, BitfinexDepth.class);
    List<LimitOrder> asks = BitfinexAdapters.adaptOrders(depthRaw.getAsks(), CurrencyPair.BTC_EUR, "ask", "");
    List<LimitOrder> bids = BitfinexAdapters.adaptOrders(depthRaw.getBids(), CurrencyPair.BTC_EUR, "bid", "");

    assertEquals(new BigDecimal("851.87"), asks.get(0).getLimitPrice());
    assertEquals(new BigDecimal("849.59"), bids.get(0).getLimitPrice());
  }
}

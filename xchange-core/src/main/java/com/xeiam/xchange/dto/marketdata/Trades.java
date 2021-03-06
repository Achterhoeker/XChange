package com.xeiam.xchange.dto.marketdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * DTO representing a collection of trades
 * </p>
 */
public final class Trades {

  private final List<Trade> trades;
  private final long lastID;
  private final TradeSortType tradeSortType;

  /**
   * Constructor
   * 
   * @param trades
   * @param tradeSortType
   */
  public Trades(List<Trade> trades, TradeSortType tradeSortType) {

    this(trades, 0L, tradeSortType);
  }

  /**
   * Constructor
   * 
   * @param trades The list of trades
   * @param lastID
   */
  public Trades(List<Trade> trades, long lastID, TradeSortType tradeSortType) {

    this.trades = new ArrayList<Trade>(trades);
    this.lastID = lastID;
    this.tradeSortType = tradeSortType;

    switch (tradeSortType) {
    case SortByTimestamp:
      Collections.sort(this.trades, new TradeTimestampComparator());
      break;
    case SortByID:
      Collections.sort(this.trades, new TradeIDComparator());
      break;

    default:
      break;
    }
  }

  /**
   * @return A list of trades ordered by id
   */
  public List<Trade> getTrades() {

    return trades;
  }

  /**
   * @return a Unique ID for the fetched trades
   */
  public long getlastID() {

    return lastID;
  }

  public TradeSortType getTradeSortType() {

    return tradeSortType;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder("Trades\n");
    for (Trade trade : getTrades()) {
      sb.append("[trade=");
      sb.append(trade.toString());
      sb.append("]\n");
    }
    return sb.toString();
  }

  public enum TradeSortType {
    SortByTimestamp, SortByID
  }

  public class TradeTimestampComparator implements Comparator<Trade> {

    @Override
    public int compare(Trade trade1, Trade trade2) {

      return trade1.getTimestamp().compareTo(trade2.getTimestamp());
    }
  }

  public class TradeIDComparator implements Comparator<Trade> {

    @Override
    public int compare(Trade trade1, Trade trade2) {

      return trade1.getId().compareTo(trade2.getId());
    }
  }

}

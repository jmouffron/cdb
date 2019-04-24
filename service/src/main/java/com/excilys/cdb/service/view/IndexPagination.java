package com.excilys.cdb.service.view;

/**
 * An enum for the different items per page in the Pagination
 * 
 * @author excilys
 *
 */
public enum IndexPagination {
  IDX_10(10), IDX_50(50), IDX_100(100);

  private int index;

  private IndexPagination(int index) {
    this.index = index;
  }

  public int getIndex() {
    return this.index;
  }
}

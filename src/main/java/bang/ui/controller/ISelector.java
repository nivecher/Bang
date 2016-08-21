/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.ui.controller;

import java.util.List;

/**
 *
 * @author Morgan
 * @param <E> type of selector
 */
public interface ISelector<E> {
    
    E select(List<E> list);
}

/*
 * Copyright (C) 2019 Gson Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gson.typeadapters;

import javax.annotation.PostConstruct;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class EnumTransformTypeAdapterFactoryTest extends TestCase {

    private enum Move {
        UP, DOWN, LEFT, RIGHT;
    }

    private static class BoardState {
        Move previousMove;
        Move nextMove;

        public BoardState(Move next, Move prev) {
            this.previousMove = prev;
            this.nextMove = next;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof BoardState)) return false;
            return equals((BoardState)obj);
        }

        public boolean equals(BoardState state) {
            return state.nextMove == nextMove
                && state.previousMove == previousMove;
        }

        public String toString() {
            return String.format("%s, %s", previousMove, nextMove);
        }
    }


    public void test() throws Exception {
        String json = "{\"previousMove\":\"up\",\"nextMove\":\"left\"}";
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory())
                .create();
        assertEquals(
            "Test derserialization",
            gson.toJson(new BoardState(Move.LEFT, Move.UP)),
            json
        );

        assertEquals(
            "Testing serialization",
            gson.fromJson(json, BoardState.class),
            new BoardState(Move.LEFT, Move.UP)
        );
    }
}

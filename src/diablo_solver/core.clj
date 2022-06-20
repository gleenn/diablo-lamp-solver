(ns diablo-solver.core)

(defn solved? [state]
  (every? true? (:state state)))

(def transitions
  {0 [0 1 3]
   1 [1 0 2 4]
   2 [2 1 5]
   3 [3 0 4 6]
   4 [4 1 3 5 7]
   5 [5 2 4 8]
   6 [6 3 7]
   7 [7 4 6 8]
   8 [8 5 7]})

(defn apply-transition [state transition]
  {:pre [(map? state) (transitions transition)]}
  (-> state
      (update :state #(reduce (fn [state toggle-index]
                                (update state toggle-index not))
                              %
                              (transitions transition)))
      (update :steps #(conj % transition))))

(defn solve
  ([current-state]
   (solve [{:state current-state :steps []}] 20))

  ([states max-depth]
   ;(prn :states)
   ;(doseq [state states]
   ;  (prn :state state))

   (if-let [solution (first (filter solved? states))]
     (:steps solution)
     (if (zero? max-depth)
       :no-solution-found
       (solve (mapcat #(map (partial apply-transition %)
                            (keys transitions))
                      states)
              (dec max-depth))))))

(defn print-state [state]
  (doseq [row (partition-all 3 (:state state))]
    (println row)))

(defn apply-transitions [state transitions]
  (reduce (fn [state transition]
            (apply-transition state transition))
          state
          transitions))

(defn generate-state-transitions [current-state]
  (let [solution (solve current-state)]
    (for [step-index (range (count solution))]
      {:apply-step (solution step-index)
       :new-state  (apply-transitions current-state (take (inc step-index) solution))})))
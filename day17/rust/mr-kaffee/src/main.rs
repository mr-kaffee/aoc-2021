use mr_kaffee_2021_17::*;
use std::time::Instant;

pub const INPUT: &str = include_str!("../input.txt");
pub const EXP_1: isize = 2_701;
pub const EXP_2: usize = 1_070;

fn main() {     
    let timer = Instant::now();
    let target = parse(INPUT);

    let timer_1 = Instant::now();
    let sol_1 = solution_1(target);
    println!("Solved part 1 in {:?}: {:?}", timer_1.elapsed(), sol_1);
    assert_eq!(EXP_1, sol_1);

    let timer_2 = Instant::now();
    let sol_2 = solution_2(target);
    println!("Solved part 2 in {:?}: {:?}", timer_2.elapsed(), sol_2);
    assert_eq!(EXP_2, sol_2);

    println!("-> Solved day 17 in {:?}", timer.elapsed());
}

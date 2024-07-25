import { useState } from "react";

const useSelectedCategory = (initialCategory: string = "all") => {
  const [selectedCategory, setSelectedCategoryState] = useState(() => {
    return localStorage.getItem("selectedCategory") || initialCategory;
  });

  const setSelectedCategory = (category: string) => {
    setSelectedCategoryState(category);
    localStorage.setItem("selectedCategory", category);
  };

  return { selectedCategory, setSelectedCategory };
};

export default useSelectedCategory;

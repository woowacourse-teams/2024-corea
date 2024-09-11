import styled from "styled-components";

export const CyclingContainer = styled.div`
  overflow: hidden;
  height: 54px;
`;

export const CyclingList = styled.ul`
  position: relative;

  display: flex;
  flex-direction: column;
  align-items: flex-end;

  width: 140px;
  height: 48px;
  padding: 0.2rem;

  font-family: "Do Hyeon", sans-serif;
  font-size: 7.2rem;

  li {
    position: absolute;
    bottom: -5rem;
    visibility: hidden;
    opacity: 0;
  }

  li.prev {
    bottom: 5rem;
    visibility: hidden;
    opacity: 1;
    transition: all 1.3s;
  }

  li.on {
    bottom: 0;
    visibility: visible;
    opacity: 1;
    transition: all 1.3s;
  }
`;

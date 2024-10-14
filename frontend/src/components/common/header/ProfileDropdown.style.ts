import styled, { keyframes } from "styled-components";

const dropdown = keyframes`
  0% {
    transform: translateY(-10%);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
`;

export const ProfileContainer = styled.div`
  position: relative;
`;

export const DropdownMenu = styled.div<{ show: boolean }>`
  position: absolute;
  z-index: 1;
  right: 0;

  display: ${({ show }) => (show ? "flex" : "none")};
  flex-direction: column;

  min-width: 200px;
  padding: 1rem;

  background-color: white;
  border-radius: 12px;
  box-shadow: 0 0 7px 1px ${({ theme }) => theme.COLOR.primary2};

  animation: ${dropdown} 0.4s ease;
`;

export const ProfileWrapper = styled.div`
  display: flex;
  gap: 0.5rem;
  padding-bottom: 1rem;
`;

export const ProfileInfo = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
  width: fit-content;

  strong {
    font: ${({ theme }) => theme.TEXT.medium_bold};
    color: ${({ theme }) => theme.COLOR.black};
  }

  span {
    font: ${({ theme }) => theme.TEXT.xSmall};
    color: ${({ theme }) => theme.COLOR.grey3};
  }
`;

export const DropdownItemWrapper = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin: 0.5rem;

  :last-child {
    gap: 0;
    border-top: 1px solid ${({ theme }) => theme.COLOR.grey1};
  }
`;

export const DropdownItem = styled.li`
  cursor: pointer;

  display: flex;
  gap: 0.3rem;
  align-items: center;

  padding: 0.5rem;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey4};

  transition: background-color 0.3s;

  &:hover {
    font: ${({ theme }) => theme.TEXT.small_bold};
    background-color: ${({ theme }) => theme.COLOR.grey0};
  }
`;

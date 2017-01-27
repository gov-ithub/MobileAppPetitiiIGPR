using Caliburn.Micro;

namespace Tickets.ViewModels
{
    public class DashboardPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;

        public DashboardPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void ShowMyTickets()
        {
            _navigationService.For<MyTicketsPageViewModel>().Navigate();
        }
    }
}
